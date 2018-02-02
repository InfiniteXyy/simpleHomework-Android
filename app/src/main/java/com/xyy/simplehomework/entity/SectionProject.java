package com.xyy.simplehomework.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by xyy on 2018/2/2.
 */

public class SectionProject extends SectionEntity<MyProject> {

    public SectionProject(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionProject(MyProject myProject) {
        super(myProject);
    }
}
