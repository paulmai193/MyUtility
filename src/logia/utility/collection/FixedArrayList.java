package logia.utility.collection;

import java.util.ArrayList;
import java.util.Collection;

import logia.utility.collection.exception.ArrayListLimitException;

import org.apache.commons.collections4.BoundedCollection;

/**
 * The Class FixedArrayList.
 *
 * @author Paul Mai
 * @param <E> the element type
 */
public class FixedArrayList<E> extends ArrayList<E> implements BoundedCollection<E> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The limit. */
	private final int         limit;

	/**
	 * Instantiates a new fixed array list.
	 *
	 * @param __c the collection
	 * @param __initialCapacity the initial capacity
	 */
	public FixedArrayList(Collection<? extends E> __c, int __initialCapacity) {
		super(__c);
		this.limit = __initialCapacity;
	}

	/**
	 * Instantiates a new fixed array list.
	 *
	 * @param __initialCapacity the __initial capacity
	 */
	public FixedArrayList(int __initialCapacity) {
		super(__initialCapacity);
		this.limit = __initialCapacity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(E __e) {
		if (isFull()) {
			throw new ArrayListLimitException();
		}
		return super.add(__e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int __index, E __element) {
		if (isFull()) {
			throw new ArrayListLimitException();
		}
		super.add(__index, __element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.ArrayList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> __c) {
		if (isFull()) {
			throw new ArrayListLimitException();
		}
		return super.addAll(__c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.ArrayList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int __index, Collection<? extends E> __c) {
		if (isFull()) {
			throw new ArrayListLimitException();
		}
		return super.addAll(__index, __c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections4.BoundedCollection#isFull()
	 */
	@Override
	public boolean isFull() {
		return this.size() >= this.limit ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections4.BoundedCollection#maxSize()
	 */
	@Override
	public int maxSize() {
		return this.limit;
	}
}
