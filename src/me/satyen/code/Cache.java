package me.satyen.code;

public interface Cache<E, T> {
	public T get(E obj);
}
