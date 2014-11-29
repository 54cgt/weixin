package net.cgt.weixin.utils.map.sort;

/***
 * 分类接口，根据V value返回K key
 * 
 * @param <K>
 * @param <V>
 */
public interface KeySortImpl<K, V> {
	/**
	 * 根据V value返回K key
	 * 
	 * @param v
	 * @return
	 */
	public K getKey(V v);
}
