/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gypsophila.athena.server.pojo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DataNode {
    
    byte[] data;
    
    private Set<String> children = null;
    
    private static final Set<String> EMPTY_SET = Collections.emptySet();
    
    /**
     * Method that inserts a child into the children set
     *
     * @param child
     *            to be inserted
     * @return true if this set did not already contain the specified element
     */
    public synchronized boolean addChild(String child) {
        if (children == null) {
            children = new HashSet<>(8);
        }
        return children.add(child);
    }
    
    /**
     * Method that deletes a child from the children set
     *
     * @param child
     *            to be inserted
     * @return true if this set contains the specified element
     */
    public synchronized boolean removeChild(String child) {
        if (children == null) {
            return false;
        }
        return children.remove(child);
    }
    
    /**
     * convenience method for setting the children for this datanode
     *
     * @param children
     */
    public synchronized void setChildren(HashSet<String> children) {
        this.children = children;
    }
    
    /**
     * convenience methods to get the children
     *
     * @return the children of this datanode. If the datanode has no children, empty
     *         set is returned
     */
    public synchronized Set<String> getChildren() {
        if (children == null) {
            // We use a static variable to return, because we want to avoid the waste of memory.
            return EMPTY_SET;
        }
        // Return an unmodifiableSet, because we want to avoid the children is modified by outer.
        return Collections.unmodifiableSet(children);
    }
    
    
}
