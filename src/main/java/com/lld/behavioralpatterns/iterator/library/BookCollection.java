package com.lld.behavioralpatterns.iterator.library;

// Aggregate interface
public interface BookCollection {
    Iterator<Book> createIterator();

    Iterator<Book> createReverseIterator();
}
