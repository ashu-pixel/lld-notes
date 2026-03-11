package com.lld.behavioralpatterns.visitor.solution.elements;

import com.lld.behavioralpatterns.visitor.solution.visitors.IRoomVisitor;

// Element interface - represents rooms(elements) that can be visited
public interface IRoom {
    void accept(IRoomVisitor visitor);

}
