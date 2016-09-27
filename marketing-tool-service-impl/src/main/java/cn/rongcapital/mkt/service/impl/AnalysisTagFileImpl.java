package cn.rongcapital.mkt.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.mongodb.TagRecommendRepository;
import cn.rongcapital.mkt.mongodb.TagTreeRepository;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;

public class AnalysisTagFileImpl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TagRecommendRepository tagRecommendRepository;

	@Autowired
	private TagTreeRepository tagTreeRepository;

	@Autowired
	MongoOperations mongoOperations;

	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 */
	public List<TagTree> readXlsx(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<String> twoTagChildrenList = new ArrayList<String>();
		List<String> oneTagChildrenList = new ArrayList<String>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				String uuid = "";
				if (xssfRow != null) {
					// 标签
					XSSFCell tagTwo = xssfRow.getCell(2);

					// 标签描述
					XSSFCell tagThree = xssfRow.getCell(3);
					// 标签内容
					XSSFCell tagFour = xssfRow.getCell(4);
					// 标签来源
					XSSFCell tagFive = xssfRow.getCell(5);
					String tagSource = getCellValue(tagFive);
					// 是否推荐标签
					XSSFCell tagSix = xssfRow.getCell(6);

					if (tagTwo != null && tagFour != null) {
						String tagName = getCellValue(tagTwo);
						// 标签存在跳出本次循环
						boolean flag = findTagRecommend(tagName);
						if (flag)
							continue;

						String tagDesc = getCellValue(tagThree);
						String tagValue = getCellValue(tagFour);

						String tagFlag = getCellValue(tagSix);
						uuid = insertTagRecommendMongoDB(tagName, tagDesc, tagValue, tagSource, tagFlag);
					}
					// -------------------------------------------------
					// 二级标签插入
					XSSFCell tagOne = xssfRow.getCell(1);

					if (tagOne != null) {
						String twoTagName = getCellValue(tagOne);
						String tagId = findTagTree(twoTagName);
						if ("".equals(tagId)) {
							ArrayList<String> arrayList = new ArrayList<String>();
							arrayList.add(uuid);
							uuid = getTagTree(twoTagName, 2, null, null, arrayList, tagSource);
						} else {

						}
					}
					// 一级标签插入
					XSSFCell tagZero = xssfRow.getCell(0);

					if (tagZero != null) {
						String oneTagName = getCellValue(tagZero);
						String tagId = findTagTree(oneTagName);
						if ("".equals(tagId)) {
							uuid = getTagTree(oneTagName, 2, null, null, null, tagSource);
							oneTagChildrenList.add(uuid);

						}
					}

				}
			}
		}
		return null;
	}

	public String findTagTree(String tagName) {
		TagTree tagTree = mongoOperations.findOne(new Query(Criteria.where("tag_name").is(tagName)), TagTree.class);

		if (tagTree != null) {
			return tagTree.getTagId();
		}
		return "";
	}

	public boolean findTagRecommend(String tagName) {

		List<TagRecommend> findByTagName = tagRecommendRepository.findByTagName(tagName);
		if (CollectionUtils.isNotEmpty(findByTagName))
			return true;
		return false;

	}

	public String getTagTree(String tagName, int level, String path, String parent, List<String> children,
			String source) {
		TagTree tagTree = new TagTree();

		String uuid = GenerateUUid.generateShortUuid();

		tagTree.setTagId(uuid);
		tagTree.setTagName(tagName);
		tagTree.setLevel(level);
		tagTree.setPath(path);
		tagTree.setParent(parent);
		tagTree.setChildren(children);
		tagTree.setSource(source);
		tagTree.setStatus(0);

		tagTreeRepository.insert(tagTree);
		return uuid;

	}

	public String insertTagRecommendMongoDB(String tagName, String tagDesc, String tagValue, String source,
			String flag) {
		TagRecommend tagRecommend = new TagRecommend();

		String uuid = GenerateUUid.generateShortUuid();

		tagRecommend.setTagId(uuid);
		tagRecommend.setTagName(tagName);
		tagRecommend.setStatus(0);
		if ("true".equals(flag.toLowerCase())) {
			tagRecommend.setFlag(true);
		} else if ("false".equals(flag.toLowerCase())) {
			tagRecommend.setFlag(false);
		} else {
			tagRecommend.setFlag(false);
		}
		tagRecommend.setTagDesc(tagDesc);
		tagRecommend.setCreateTime(new Date());
		tagRecommend.setUpdateTime(new Date());
		tagRecommend.setSource(source);

		String[] split = tagValue.split("/");
		List<String> tagList = java.util.Arrays.asList(split);
		;
		tagRecommend.setTagList(tagList);

		tagRecommendRepository.insert(tagRecommend);

		return uuid;
	}

	private String getCellValue(XSSFCell cell) {
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = formatter.formatCellValue(cell);
				} else {
					double value = cell.getNumericCellValue();
					int intValue = (int) value;
					cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = "";
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue.trim();
	}

}
