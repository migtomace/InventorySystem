package Model;

import Model.Part;

public class InHouse  extends Part {
    private int machineid;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineid){
        setPartID(id);
        setPartName(name);
        setPartPrice(price);
        setPartInv(stock);
        setPartMin(min);
        setPartMax(max);
        setMachineid(machineid);
    }

    public int getMachineid() {
        return machineid;
    }

    public void setMachineid(int machineid) {
        this.machineid = machineid;
    }
}
