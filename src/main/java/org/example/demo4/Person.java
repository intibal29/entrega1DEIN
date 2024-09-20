package org.example.demo4;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que representa a una persona con detalles como nombre, apellido y fecha de nacimiento.
 * Incluye validaciones y categorización por edad.
 */
public class Person {
    private static AtomicInteger personSequence = new AtomicInteger(0); // Secuencial para asignar IDs únicos
    private int personId;   // Identificador único de la persona
    private String firstName;  // Nombre de la persona
    private String lastName;   // Apellido de la persona
    private LocalDate birthDate; // Fecha de nacimiento de la persona

    /**
     * Enum para categorizar las edades de las personas.
     */
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    }

    /**
     * Constructor por defecto que inicializa una persona vacía.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor que inicializa una persona con el nombre, apellido y fecha de nacimiento dados.
     *
     * @param firstName Nombre de la persona.
     * @param lastName Apellido de la persona.
     * @param birthDate Fecha de nacimiento de la persona.
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * Obtiene el identificador de la persona.
     *
     * @return El ID de la persona.
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Establece el identificador de la persona.
     *
     * @param personId El nuevo ID de la persona.
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return El nombre de la persona.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param firstName El nuevo nombre de la persona.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el apellido de la persona.
     *
     * @return El apellido de la persona.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido de la persona.
     *
     * @param lastName El nuevo apellido de la persona.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene la fecha de nacimiento de la persona.
     *
     * @return La fecha de nacimiento de la persona.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Establece la fecha de nacimiento de la persona.
     *
     * @param birthDate La nueva fecha de nacimiento de la persona.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Valida la fecha de nacimiento de la persona.
     *
     * @param bdate La fecha de nacimiento a validar.
     * @return true si la fecha es válida; false de lo contrario.
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>());
    }

    /**
     * Valida la fecha de nacimiento de la persona y añade errores a la lista si es necesario.
     *
     * @param bdate La fecha de nacimiento a validar.
     * @param errorList Lista donde se agregarán los mensajes de error.
     * @return true si la fecha es válida; false de lo contrario.
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true;
        }
        // La fecha de nacimiento no puede estar en el futuro
        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("Birth date must not be in future.");
            return false;
        }
        return true;
    }

    /**
     * Valida la información de la persona.
     *
     * @param errorList Lista donde se agregarán los mensajes de error.
     * @return true si la persona es válida; false de lo contrario.
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList);
    }

    /**
     * Valida la información de otra persona y añade errores a la lista si es necesario.
     *
     * @param p La persona a validar.
     * @param errorList Lista donde se agregarán los mensajes de error.
     * @return true si la persona es válida; false de lo contrario.
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true;
        String fn = p.getFirstName();
        if (fn == null || fn.trim().length() == 0) {
            errorList.add("First name must contain minimum one character.");
            isValid = false;
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().length() == 0) {
            errorList.add("Last name must contain minimum one character.");
            isValid = false;
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Obtiene la categoría de edad de la persona.
     *
     * @return La categoría de edad de la persona.
     */
    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN;
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY;
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD;
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN;
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT;
        } else if (years > 50) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.UNKNOWN;
        }
    }

    /**
     * Guarda la información de la persona si es válida.
     *
     * @param errorList Lista donde se agregarán los mensajes de error si la persona no es válida.
     * @return true si la persona se guardó correctamente; false de lo contrario.
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false;
        if (isValidPerson(errorList)) {
            System.out.println("Saved " + this.toString());
            isSaved = true;
        }
        return isSaved;
    }

    /**
     * Devuelve una representación en cadena de la persona.
     *
     * @return Una cadena que representa a la persona.
     */
    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }
}
