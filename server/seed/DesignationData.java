package bit.project.server.seed;

import java.util.Hashtable;
import bit.project.server.util.seed.SeedClass;
import bit.project.server.util.seed.AbstractSeedClass;

@SeedClass
public class DesignationData extends AbstractSeedClass {

    public DesignationData(){
        addIdNameData(1, "Manager");
        addIdNameData(2, "Receptionist");
    }

}