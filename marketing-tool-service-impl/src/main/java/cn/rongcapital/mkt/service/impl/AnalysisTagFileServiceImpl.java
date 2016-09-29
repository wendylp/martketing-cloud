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
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.mongodb.TagRecommendRepository;
import cn.rongcapital.mkt.mongodb.TagTreeRepository;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.AnalysisTagFileService;

@Service
public class AnalysisTagFileServiceImpl implements AnalysisTagFileService{

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
	 * 
	 */
	@Override
	public List<TagTree> readXlsx(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
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
					
					// 一级标签
					XSSFCell tagZero = xssfRow.getCell(0);
					String oneTagName = getCellValue(tagZero);
					
					// 标签
					XSSFCell tagTwo = xssfRow.getCell(2);

					// 标签描述
					XSSFCell tagThree = xssfRow.getCell(3);
					// 标签内容
					XSSFCell tagFour = xssfRow.getCell(4);
					// 标签来源
					XSSFCell tagFive = xssfRow.getCell(5);
					// 是否推荐标签
					XSSFCell tagSix = xssfRow.getCell(6);
					
					String tagSource = getCellValue(tagSix);
					// 是否推荐标签
					XSSFCell tagSeven = xssfRow.getCell(7);

					if (tagTwo != null && tagFive != null) {
						String tagName = getCellValue(tagTwo);
						// 标签存在跳出本次循环
						boolean flag = findTagRecommend(tagName);
						if (flag)
							continue;

						String tagNameEng = getCellValue(tagThree);
						String tagDesc = getCellValue(tagFour);
						String tagValue = getCellValue(tagFive);

						String tagFlag = getCellValue(tagSeven);
						uuid = insertTagRecommendMongoDB(tagName, tagNameEng, tagDesc, tagValue, tagSource, tagFlag);
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
							uuid = getTagTree(twoTagName, 2, null, oneTagName, arrayList, tagSource);
						} else {
							// 更新Children标签
							TagTree tagTree = getTagTree(twoTagName);
							List<String> childrenLists = tagTree.getChildren();
							if(childrenLists != null) {
								if(childrenLists.contains(uuid) == false) {
									childrenLists.add(uuid);
									getTagTree(twoTagName, 2, null, null, childrenLists, tagSource);
								} else {
									childrenLists = new ArrayList<String>();
									childrenLists.add(uuid);
									getTagTree(twoTagName, 2, null, null, childrenLists, tagSource);
								}
							}
						}
					}
					
					
					// 一级标签插入
					if (tagZero != null) {
						String tagId = findTagTree(oneTagName);
						if ("".equals(tagId)) {
							ArrayList<String> arrayList = new ArrayList<String>();
							arrayList.add(uuid);
							uuid = getTagTree(oneTagName, 1, null, null, arrayList, tagSource);

						} else {
							// 更新Children标签
							TagTree tagTree = getTagTree(oneTagName);
							List<String> childrenLists = tagTree.getChildren();
							if(childrenLists != null) {
								if(childrenLists.contains(uuid) == false) {
									childrenLists.add(uuid);
									getTagTree(oneTagName, 1, null, null, childrenLists, tagSource);
								
							} else {
								childrenLists = new ArrayList<String>();
								childrenLists.add(uuid);
								getTagTree(oneTagName, 1, null, null, childrenLists, tagSource);
							}
						}
					}
				}
				}
			}
		}
		xssfWorkbook.close();
		return null;
	}

	public String findTagTree(String tagName) {
		TagTree tagTree = mongoOperations.findOne(new Query(Criteria.where("tag_name").is(tagName)), TagTree.class);

		if (tagTree != null) {
			return tagTree.getTagId();
		}
		return "";
	}
	
	/**
	 * 根据tagName获取TagTree
	 * @param tagName
	 * @return
	 */
	public TagTree getTagTree(String tagName) {
		TagTree tagTree = mongoOperations.findOne(new Query(Criteria.where("tag_name").is(tagName)), TagTree.class);

		return tagTree;
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

		tagTree.setTagName(tagName);
		tagTree.setLevel(level);
		tagTree.setPath(path);
		tagTree.setParent(parent);
		tagTree.setChildren(children);
		tagTree.setSource(source);
		tagTree.setStatus(0);
		tagTree.setCreateTime(new Date());
		tagTree.setUpdateTime(new Date());

		TagTree tagTreeByTagName = getTagTree(tagName);
		if(tagTreeByTagName == null) {
			// 如果不存在，新插入
			tagTree.setTagId(uuid);
			tagTreeRepository.insert(tagTree);
		} else {
			// 如果已经存在，修改
			tagTreeByTagName.setChildren(children);
			tagTreeRepository.save(tagTreeByTagName);
		}
		return uuid;

	}

	public String insertTagRecommendMongoDB(String tagName, String tagNameEng, String tagDesc, String tagValue, String source,
			String flag) {
		TagRecommend tagRecommend = new TagRecommend();

		String uuid = GenerateUUid.generateShortUuid();

		tagRecommend.setTagId(uuid);
		tagRecommend.setTagName(tagName);
		tagRecommend.setTagNameEng(tagNameEng);
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
