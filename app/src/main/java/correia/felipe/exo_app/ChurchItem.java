package correia.felipe.exo_app;

/**
 * Created by Felipe on 13/10/2017.
 */


public class ChurchItem {
    private String name;
    private String id;

    public ChurchItem() {
        super();
    }

    public ChurchItem(String name_1, String id_1){
        name_1 = name;
        id_1 = id;

    }

    public void setName(String name)
    {
        this.name=name;
    }
    public void setId(String id)
    {
        this.id=id;
    }

    public String getName()
    {
        return this.name;
    }
    public String getId()
    {
        return this.id;
    }
}
