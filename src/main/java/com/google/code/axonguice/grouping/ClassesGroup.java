/*
 * Copyright (C) 2013 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.axonguice.grouping;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * CommandHandlersGroup - TODO: description
 * todo allow concrete specification of classes set
 * @author Alexey Krylov (lexx)
 * @since 07.02.13
 */
public class ClassesGroup {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private Collection<String> packages;
    private Pattern inclusionPattern;
    private Pattern exclusionPattern;
    private Predicate<Class> inclusionPredicate;
    private Predicate<Class> exclusionPredicate;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public ClassesGroup(String... packages) {
        this(Arrays.asList(packages));
    }

    public ClassesGroup(Collection<String> packages) {
        this.packages = new ArrayList<>(packages);
    }

    /*===========================================[ CLASS METHODS ]================*/

    public Collection<String> getPackages() {
        return Collections.unmodifiableCollection(packages);
    }

    protected void setIncusionPattern(String inclusionPattern) {
        if (inclusionPattern != null && !inclusionPattern.isEmpty()) {
            this.inclusionPattern = Pattern.compile(inclusionPattern);
            if (inclusionPredicate == null) {
                // inclusionPattern is a strict parameter - we need to deny other matcher possibilities
                inclusionPredicate = ClassesGroupFilterPredicates.DenyAll;
            }
        }
    }

    protected void setExclusionPattern(String exclusionPattern) {
        if (exclusionPattern != null && !exclusionPattern.isEmpty()) {
            this.exclusionPattern = Pattern.compile(exclusionPattern);
        }
    }

    protected void setInclusionFilterPredicate(Predicate<Class> inclusionPredicate) {
        if (inclusionPredicate != null) {
            this.inclusionPredicate = inclusionPredicate;
            if (inclusionPattern == null) {
                // strict parameters set - we need to deny other matcher possibilities
                inclusionPattern = Pattern.compile(ClassesGroupPatterns.DenyAll);
            }
        }
    }

    protected void setExclusionFilterPredicate(Predicate<Class> exclusionPredicate) {
        if (exclusionPredicate != null) {
            this.exclusionPredicate = exclusionPredicate;
        }
    }

    public Predicate<Class> getInclusionFilterPredicate() {
        return inclusionPredicate;
    }

    public Predicate<Class> getExclusionFilterPredicate() {
        return exclusionPredicate;
    }

    public boolean matches(Class<?> aClass) {
        Preconditions.checkNotNull(aClass);
        String className = aClass.getName();

        boolean matches = false;
        if (isInclusionPatternMatches(className) || isInclusionFilterPredicateMatches(aClass)) {
            matches = true;
        }

        if (matches) {
            if (isExclusionPatternMatches(className) || isExclusionFilterPredicateMatches(aClass)) {
                matches = false;
            }
        }

        return matches;
    }

    private boolean isInclusionPatternMatches(CharSequence className) {
        return inclusionPattern == null || inclusionPattern.matcher(className).matches();
    }

    private boolean isInclusionFilterPredicateMatches(Class<?> handlerClass) {
        return inclusionPredicate == null || inclusionPredicate.apply(handlerClass);
    }

    private boolean isExclusionPatternMatches(CharSequence className) {
        return exclusionPattern != null && exclusionPattern.matcher(className).matches();
    }

    private boolean isExclusionFilterPredicateMatches(Class<?> handlerClass) {
        return exclusionPredicate != null && exclusionPredicate.apply(handlerClass);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClassesGroup)) {
            return false;
        }

        ClassesGroup handlersClassesGroup = (ClassesGroup) obj;

        if (exclusionPattern != null ? !exclusionPattern.equals(handlersClassesGroup.exclusionPattern) : handlersClassesGroup.exclusionPattern != null) {
            return false;
        }
        if (exclusionPredicate != null ? !exclusionPredicate.equals(handlersClassesGroup.exclusionPredicate) : handlersClassesGroup.exclusionPredicate != null) {
            return false;
        }
        if (inclusionPattern != null ? !inclusionPattern.equals(handlersClassesGroup.inclusionPattern) : handlersClassesGroup.inclusionPattern != null) {
            return false;
        }
        if (inclusionPredicate != null ? !inclusionPredicate.equals(handlersClassesGroup.inclusionPredicate) : handlersClassesGroup.inclusionPredicate != null) {
            return false;
        }
        if (packages != null ? !packages.equals(handlersClassesGroup.packages) : handlersClassesGroup.packages != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = packages != null ? packages.hashCode() : 0;
        result = 31 * result + (inclusionPattern != null ? inclusionPattern.hashCode() : 0);
        result = 31 * result + (exclusionPattern != null ? exclusionPattern.hashCode() : 0);
        result = 31 * result + (inclusionPredicate != null ? inclusionPredicate.hashCode() : 0);
        result = 31 * result + (exclusionPredicate != null ? exclusionPredicate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group");
        sb.append("{commandHandlersPackages=").append(packages).append('\'');
        sb.append(", inclusionPattern=").append(inclusionPattern);
        sb.append(", exclusionPattern=").append(exclusionPattern);
        sb.append('}');
        return sb.toString();
    }

    /*===========================================[ GETTER/SETTER ]================*/

    public Pattern getInclusionPattern() {
        return inclusionPattern;
    }

    public Pattern getExclusionPattern() {
        return exclusionPattern;
    }
}
