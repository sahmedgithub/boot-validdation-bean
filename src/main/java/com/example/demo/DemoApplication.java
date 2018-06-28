package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.lang.annotation.*;
import java.util.function.Consumer;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}


/*=======================Controller============*/
@RestController
class Restti {

	@PostMapping(path = "/hello")
	public String hello(@Valid @RequestBody User user) {
		Consumer<String> c = (x) -> System.out.println(x);
        Consumer<String> c2 = (x) -> System.out.println(x);
        Consumer<String> c6 = (y) -> System.out.println("f");

        c.andThen(c2);
        c.andThen(c6);
        c.accept("Babu");


        return String.format("Hello, %s", user.getName());
	}

	public void pp() {
        Consumer < String > consumer1 = (arg) -> {
                System.out.println(arg + "OK");
        };
        consumer1.accept("TestConsumerAccept - ");
        Consumer < String > consumer2 = (x) -> {
                System.out.println(x + "OK!!!");
        };
        consumer1.andThen(consumer2).accept("TestConsumerAfterThen - ");
    }
}

/*===============Model============*/
@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
	@NameConstraint(message = "Name is not valid for user")
	private String name;
}


/*======================Bean validation Annotation================*/

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@interface NameConstraint {
	String message() default "Invalid Name provided";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

/*=====================Bean validation Implementation=============*/

class NameValidator implements
		ConstraintValidator<NameConstraint, String> {

	@Override
	public void initialize(NameConstraint contactNumber) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext constraintValidatorContext) {
		return contactField != null && contactField.matches("[a-zA-Z]+")
				&& (contactField.length() > 8) && (contactField.length() < 14);
	}


}
