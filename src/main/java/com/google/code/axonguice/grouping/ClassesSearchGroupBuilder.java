/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.grouping;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * ClassesGroupBuilder - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 15.02.13
 */
public class ClassesSearchGroupBuilder {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private Collection<String> packages;
    private String inclusionPattern;
    private String exclusionPattern;
    private Predicate<Class> inclusionPredicate;
    private Predicate<Class> exclusionPredicate;

	/*===========================================[ CONSTRUCTORS ]=================*/

    protected ClassesSearchGroupBuilder(Collection<String> packages) {
        this.packages = new ArrayList<>(packages);
    }

	/*===========================================[ CLASS METHODS ]================*/

    public static ClassesSearchGroupBuilder forPackage(@NotNull @Size(min = 1) String packageName) {
        Preconditions.checkArgument(packageName != null && !packageName.isEmpty(), "Package name is null");
        return forPackages(Arrays.asList(packageName));
    }

    public static ClassesSearchGroupBuilder forPackages(@NotNull @Size(min = 1) Collection<String> packages) {
        Preconditions.checkArgument(packages != null && !packages.isEmpty(), "Packages is null or empty");
        return new ClassesSearchGroupBuilder(packages);
    }

    public static ClassesSearchGroupBuilder forPackages(@NotNull @Size(min = 1) String... packages) {
        Preconditions.checkArgument(packages != null && packages.length > 0, "Packages is null or empty");
        return new ClassesSearchGroupBuilder(Arrays.asList(packages));
    }

    public ClassesSearchGroupBuilder withPackage(@NotNull @Size(min = 1) String packageName) {
        Preconditions.checkArgument(packageName != null && !packageName.isEmpty(), "Package name is null or empty");
        packages.add(packageName);
        return this;
    }

    public ClassesSearchGroupBuilder withPackages(@NotNull @Size(min = 1) Collection<String> packages) {
        Preconditions.checkArgument(packages != null && !packages.isEmpty(), "Packages is null or empty");
        this.packages.addAll(packages);
        return this;
    }

    public ClassesSearchGroupBuilder withInclusionPattern(@NotNull @Size(min = 1) String inclusionPattern) {
        Preconditions.checkArgument(packages != null && !packages.isEmpty(), "Inclusion pattern is null or empty");
        this.inclusionPattern = inclusionPattern;
        return this;
    }

    public ClassesSearchGroupBuilder withExclusionPattern(@NotNull @Size(min = 1) String exclusionPattern) {
        Preconditions.checkArgument(packages != null && !packages.isEmpty(), "Exclusion pattern is null or empty");
        this.exclusionPattern = exclusionPattern;
        return this;
    }

    public ClassesSearchGroupBuilder withInclusionFilterPredicate(@NotNull Predicate<Class> inclusionPredicate) {
        Preconditions.checkArgument(inclusionPredicate != null, "Inclusion predicate is null");
        this.inclusionPredicate = inclusionPredicate;
        return this;
    }

    public ClassesSearchGroupBuilder withExclusionFilterPredicate(@NotNull Predicate<Class> exclusionPredicate) {
        Preconditions.checkArgument(inclusionPredicate != null, "Exclusion predicate is null");
        this.exclusionPredicate = exclusionPredicate;
        return this;
    }

    public ClassesSearchGroup build() {
        ClassesSearchGroup repositoriesGroup = new ClassesSearchGroup(packages);
        repositoriesGroup.setIncusionPattern(inclusionPattern);
        repositoriesGroup.setExclusionPattern(exclusionPattern);
        repositoriesGroup.setInclusionFilterPredicate(inclusionPredicate);
        repositoriesGroup.setExclusionFilterPredicate(exclusionPredicate);
        return repositoriesGroup;
    }
}