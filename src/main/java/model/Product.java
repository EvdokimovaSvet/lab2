package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.time.LocalDate;
@XmlRootElement( name = "Product")
public class Product {

    private int idProduct;
    private String nameOfProduct;
    private String countryOfOrigin;
    private int weight;
    private LocalDate dateOfCreate;
    private LocalDate dateOfDeath;
    private int idWare;

    public int getIdOfProduct() {
        return idProduct;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public String getCountry() {
        return countryOfOrigin;
    }

    public int getWeight() {
        return weight;
    }

    public LocalDate getDateOfCreate() {
        return dateOfCreate;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public int getIdWare() {return idWare;}

    public Object setIdProduct(int id) {
        this.idProduct = id;
        return this;
    }
    public Object setName(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
        return this;
    }

    public Object setCountry(String country) {
        this.countryOfOrigin = country;
        return this;
    }

    public Object setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public Object setDateOfCreate(int year, int month, int day) {
        this.dateOfCreate = LocalDate.of(year, month, day);
        return this;
    }

    public Object setDateOfDeath(int year, int month, int day) {
        this.dateOfDeath = LocalDate.of(year, month, day);
        return this;
    }

    public Object setIdWare(int id) {
        this.idWare = id;
        return this;
    }


    @Override
    public String toString() {
        return "\nName of product: " + nameOfProduct + "\nCountry of origin: " + countryOfOrigin + "\nWeight: "
                + weight + "\nDate of create: " + dateOfCreate + "\nDate of death: "
                + dateOfDeath + "\n";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((countryOfOrigin == null) ? 0 : countryOfOrigin.hashCode());
        result = prime * result + ((dateOfCreate == null) ? 0 : dateOfCreate.hashCode());
        result = prime * result + ((dateOfDeath == null) ? 0 : dateOfDeath.hashCode());
        result = prime * result + ((nameOfProduct == null) ? 0 : nameOfProduct.hashCode());
        result = prime * result + weight;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        if (countryOfOrigin == null) {
            if (other.countryOfOrigin != null) {
                return false;
            }
        } else if (!countryOfOrigin.equals(other.countryOfOrigin)) {
            return false;
        }
        if (dateOfCreate == null) {
            if (other.dateOfCreate != null) {
                return false;
            }
        } else if (!dateOfCreate.equals(other.dateOfCreate)) {
            return false;
        }
        if (dateOfDeath == null) {
            if (other.dateOfDeath != null) {
                return false;
            }
        } else if (!dateOfDeath.equals(other.dateOfDeath)) {
            return false;
        }
        if (nameOfProduct == null) {
            if (other.nameOfProduct != null) {
                return false;
            }
        } else if (!nameOfProduct.equals(other.nameOfProduct)) {
            return false;
        }
        if (weight != other.weight) {
            return false;
        }
        return true;
    }

    public boolean isValidProduct() {
        LocalDate today = LocalDate.now();
        if (dateOfDeath.isBefore(today)) return false;
        else return true;
    }
}

