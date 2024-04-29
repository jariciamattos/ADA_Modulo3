package com.example.banco.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {
       /* Calendar dateInCalendar = Calendar.getInstance();
        dateInCalendar.setTime(valueToValidate);
*/
      //  return Calendar.getInstance().get(Calendar.YEAR) - dateInCalendar.get(Calendar.YEAR) >= 18;
        return Period.between(valueToValidate, LocalDate.now()).getYears() >= 18;
    }
}