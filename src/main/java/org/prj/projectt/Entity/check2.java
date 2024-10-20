package org.prj.projectt.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class check2 {
    @Autowired
    private check a;

    public check2() {
    }
    public void chekck2()
    {
        a.check();
    }
}
