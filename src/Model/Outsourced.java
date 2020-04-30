package Model;

import Model.Part;

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        setPartID(id);
        setPartName(name);
        setPartPrice(price);
        setPartInv(stock);
        setPartMin(min);
        setPartMax(max);
        setCompanyName(companyName);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
