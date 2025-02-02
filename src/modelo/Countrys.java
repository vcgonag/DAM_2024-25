package modelo;

/**
 * Clase que representa un país con información sobre su población y otros datos demográficos.
 */
public class Countrys {

    private String id, nameCountry, population, denalty, area, fertility, age, urban, share;

    /**
     * Constructor por defecto.
     */
    public Countrys() {
        super();
    }

    /**
     * Constructor con parámetros para inicializar un país.
     * 
     * @param id          Identificador del país.
     * @param nameCountry Nombre del país.
     * @param population  Población del país.
     * @param denalty     Densidad de población.
     * @param area        Área del país.
     * @param fertility   Tasa de fertilidad.
     * @param age         Edad media de la población.
     * @param urban       Porcentaje de población urbana.
     * @param share       Porcentaje de participación en la población global.
     */
    public Countrys(String id, String nameCountry, String population, String denalty, String area, 
                    String fertility, String age, String urban, String share) {
        this.id = id;
        this.nameCountry = nameCountry;
        this.population = population;
        this.denalty = denalty;
        this.area = area;
        this.fertility = fertility;
        this.age = age;
        this.urban = urban;
        this.share = share;
    }

    /**
     * Obtiene el ID del país.
     * 
     * @return ID del país.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el ID del país.
     * 
     * @param id Nuevo ID del país.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del país.
     * 
     * @return Nombre del país.
     */
    public String getNameCountry() {
        return nameCountry;
    }

    /**
     * Establece el nombre del país.
     * 
     * @param nameCountry Nuevo nombre del país.
     */
    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    /**
     * Obtiene la población del país.
     * 
     * @return Población del país.
     */
    public String getPopulation() {
        return population;
    }

    /**
     * Establece la población del país.
     * 
     * @param population Nueva población del país.
     */
    public void setPopulation(String population) {
        this.population = population;
    }

    /**
     * Obtiene la densidad de población.
     * 
     * @return Densidad de población.
     */
    public String getDenalty() {
        return denalty;
    }

    /**
     * Establece la densidad de población.
     * 
     * @param denalty Nueva densidad de población.
     */
    public void setDenalty(String denalty) {
        this.denalty = denalty;
    }

    /**
     * Obtiene el área del país.
     * 
     * @return Área del país.
     */
    public String getArea() {
        return area;
    }

    /**
     * Establece el área del país.
     * 
     * @param area Nueva área del país.
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Obtiene la tasa de fertilidad.
     * 
     * @return Tasa de fertilidad.
     */
    public String getFertility() {
        return fertility;
    }

    /**
     * Establece la tasa de fertilidad.
     * 
     * @param fertility Nueva tasa de fertilidad.
     */
    public void setFertility(String fertility) {
        this.fertility = fertility;
    }

    /**
     * Obtiene la edad media de la población.
     * 
     * @return Edad media.
     */
    public String getAge() {
        return age;
    }

    /**
     * Establece la edad media de la población.
     * 
     * @param age Nueva edad media.
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Obtiene el porcentaje de población urbana.
     * 
     * @return Porcentaje de población urbana.
     */
    public String getUrban() {
        return urban;
    }

    /**
     * Establece el porcentaje de población urbana.
     * 
     * @param urban Nuevo porcentaje de población urbana.
     */
    public void setUrban(String urban) {
        this.urban = urban;
    }

    /**
     * Obtiene el porcentaje de participación en la población global.
     * 
     * @return Porcentaje de participación en la población global.
     */
    public String getShare() {
        return share;
    }

    /**
     * Establece el porcentaje de participación en la población global.
     * 
     * @param share Nuevo porcentaje de participación en la población global.
     */
    public void setShare(String share) {
        this.share = share;
    }

    /**
     * Representación en cadena del objeto Countrys.
     * 
     * @return Una cadena con los datos del país.
     */
    @Override
    public String toString() {
        return "Country [nameCountry= " + nameCountry + ", population= " + population + ", denalty= " + denalty
                + ", area= " + area + ", fertility= " + fertility + ", age= " + age + ", urban= " + urban + ", share= "
                + share + "]";
    }
}
