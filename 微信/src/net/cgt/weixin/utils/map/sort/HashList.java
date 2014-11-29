package net.cgt.weixin.utils.map.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;

/***
 * 键值对索引排序的工具类 HashMap简单排序的一种实现
 * 
 * @param <K>
 * @param <V>
 */
public class HashList<K, V> {

	private static final String LOGTAG = LogUtil.makeLogTag(HashList.class);

	/**
	 * 键集合(Group)
	 */
	private List<K> mList_key = new ArrayList<K>();
	/**
	 * 键值对映射(Child)
	 */
	private HashMap<K, List<V>> mMap = new HashMap<K, List<V>>();
	/**
	 * 键值分类
	 */
	private KeySortImpl<K, V> keySortImpl;

	/**********************************************************************************************************/
	/**
	 * 构造方法
	 * 
	 * @param keySort 分类排序接口
	 */
	public HashList(KeySortImpl<K, V> keySortImpl) {
		this.keySortImpl = keySortImpl;
	}

	/**
	 * 根据value值返回key
	 */
	public K getKey(V v) {
		return keySortImpl.getKey(v);
	}

	/**
	 * 键值对排序(Group集合排序)
	 * 
	 * @param comparator 比较器
	 */
	public void sortKeyComparator(Comparator<K> comparator) {
		Collections.sort(mList_key, comparator);
	}

	/**
	 * 根据索引返回键值(根据index获取Group中的Value,其中该返回的Value是Child的key)
	 * 
	 * @param index
	 * @return
	 */
	public K getKeyThroughIndex(int index) {
		return mList_key.get(index);
	}

	/**
	 * 根据索引返回值的集合(根据key获取Child中的Value,该Value是一个List集合)
	 * 
	 * @param index
	 * @return
	 */
	public List<V> getValueListThroughIndex(int index) {
		return mMap.get(getKeyThroughIndex(index));
	}

	/**
	 * 根据索引及键,返回值(根据index和key,获得Child中的最终的一条item数据)
	 * 
	 * @param index 索引
	 * @param key 键
	 * @return
	 */
	public V getValueThroughIndexAndKey(int index, int key) {
		return getValueListThroughIndex(index).get(key);
	}

	/**
	 * 集合长度
	 * 
	 * @return
	 */
	public int size() {
		return mList_key.size();
	}

	/**
	 * 清空集合(清空Child)
	 */
	public void clear() {
		for (Iterator<K> it = mMap.keySet().iterator(); it.hasNext(); mMap.remove(it.next()))
			;
	}

	/**
	 * 返回是否包含(全部为false即不包含)
	 * 
	 * @param object
	 * @return false<>
	 */
	public boolean contains(Object object) {
		return false;
	}

	public boolean isEmpty() {
		return false;
	}

	public Object remove(int location) {
		return null;
	}

	public boolean remove(Object object) {
		return false;
	}

	public boolean removeAll(Collection arg0) {
		return false;
	}

	public boolean retainAll(Collection arg0) {
		return false;
	}

	/**
	 * 替换"键集合"指定位置的值(Group)
	 * 
	 * @param location
	 * @param object
	 * @return
	 */
	public Object set(int location, Object object) {
		return mList_key.set(location, (K) object);
	}

	/**
	 * 返回"键集合"从start到end段的集合(Group)
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<K> subList(int start, int end) {
		return mList_key.subList(start, end);
	}

	/**
	 * 将"键集合"转为数组(Group)
	 * 
	 * @return
	 */
	public Object[] toArray() {
		return mList_key.toArray();
	}

	/**
	 * 将"键集合"转为数组(Group)
	 * 
	 * @param array 数组
	 * @return
	 */
	public Object[] toArray(Object[] array) {
		return mList_key.toArray(array);
	}

	/**
	 * 给集合添加值
	 * 
	 * @param object
	 * @return
	 */
	public boolean add(Object object) {
		V v = (V) object;
		K key = getKey(v);
		if (!mMap.containsKey(key)) {
			List<V> list = new ArrayList<V>();
			L.i(LOGTAG, "HashList--->key=" + key + "--------------------Value=" + v);
			list.add(v);
			mList_key.add(key);
			mMap.put(key, list);
		} else {
			mMap.get(key).add(v);
			L.i(LOGTAG, "HashList--->key=" + key + "---V=" + v);
		}
		return false;
	}

	/**
	 * 返回"键集合"中某个k在集合中的角标(Group)
	 * 
	 * @param k
	 * @return
	 */
	public int indexOfKey(K k) {
		return mList_key.indexOf(k);
	}
}
