package com.xyy.simplehomework.cards;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.xyy.simplehomework.entity.MyProject;

/**
 * Created by xyy on 2018/2/2.
 */

public class SectionProject extends SectionEntity<MyProject>{

    public SectionProject(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionProject(MyProject myProject) {
        super(myProject);
    }
}
