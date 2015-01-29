package net.brdloush.thymeleaf.perfissue;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brdloush on 29.1.2015.
 */
public class Model {

    private List<Phone> phones = new LinkedList<Phone>();

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
