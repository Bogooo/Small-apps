package Model;

public class Clients implements Comparable{
    private int id_client;
    private String name;
    private String email;

    public Clients(int idClient, String name, String email) {
        this.id_client = idClient;
        this.name = name;
        this.email = email;
    }
    public Clients() {
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(Object o) {
        Clients c= (Clients) o;
        return this.id_client-c.getId_client();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clients client = (Clients) o;

        if (getId_client() != client.getId_client()) return false;
        if (!getName().equals(client.getName())) return false;
        return getEmail().equals(client.getEmail());
    }

    @Override
    public int hashCode() {
        int result = getId_client();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.id_client +"  "+this.name;
    }
}
