package hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity;

public class Store {

    public String storeName, address;
    public byte[] imgStore;


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImgStore() {
        return imgStore;
    }

    public void setImgStore(byte[] imgStore) {
        this.imgStore = imgStore;
    }

    public Store(String storeName, String address, byte[] imgStore) {
        this.storeName = storeName;
        this.address = address;
        this.imgStore = imgStore;
    }

}
