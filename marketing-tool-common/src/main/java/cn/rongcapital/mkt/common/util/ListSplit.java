package cn.rongcapital.mkt.common.util;

import java.util.ArrayList;
import java.util.List;

public class ListSplit {

	/**
	 * list分隔为多个子list
	 * @param parentList
	 * @param childSize
	 * @return
	 */
	public static <T>  List<List<T>> getListSplit(List<T> parentList, int childSize){
		//集合为空则返回空
		if(parentList == null){
			return null;
		}
		//原list大小
		Integer parentSize = parentList.size();
		//用于保存新的返回list
		List<List<T>> lists = new ArrayList<List<T>>();
		
		if(childSize >= parentSize || childSize < 1){
			lists.add(parentList); 
		}else{
			
			//分组数
			int part = (parentSize%childSize == 0) ? (parentSize/childSize) : (parentSize/childSize + 1);//分批数
			
			for(int i = 0 ; i < part ; i++){
				
				List<T> listPage = new ArrayList<T>();
				
				for(int j = 0 ; j < childSize && (i*childSize + j) < parentSize ; j++){
					
					listPage.add(parentList.get(i*childSize + j));
					
				}
				lists.add(listPage);
			}
		}
		return lists;
	}
}

