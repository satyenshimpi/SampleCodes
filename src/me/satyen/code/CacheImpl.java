package me.satyen.code;

import java.util.HashMap;

public class CacheImpl<E, T> implements Cache<E, T>{
	//HashMap is easy implementation but allows null key. 
	//hashtable is synchronized version. so unless synchronization needed we should avoid
	HashMap<E, T> cacheMap = new HashMap<E, T>();
	
	@Override
	public T get(E obj) {
		T ret = null;
		if(obj == null){
			return null;
		}
		ret = checkCache(obj);
		if(ret == null){
			ret = getFromSource(obj);
		}
		return ret;
	}

	private T checkCache(E obj){
		return cacheMap.get(obj);
	}
	
	private void addToCache(E key, T o){
		cacheMap.put(key, o);
	}
	
	private T getFromSource(E obj){
		//get from DB/ file/ or web
		T gotObj = createNew();
		if(gotObj != null)
			addToCache(obj, gotObj);
		return null;
	}
	
	//this is for simplicity. not a part of algo
	private T createNew(){
		return (T) new Object();
	}
}
