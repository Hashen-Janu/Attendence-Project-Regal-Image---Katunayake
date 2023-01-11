package bit.project.server.seed;

import bit.project.server.util.seed.AbstractSeedClass;
import bit.project.server.util.seed.SeedClass;

@SeedClass
public class DepartmentData extends AbstractSeedClass {
    public DepartmentData(){
        addIdNameData(1, "IT");
        addIdNameData(2, "HR");
    }
}
