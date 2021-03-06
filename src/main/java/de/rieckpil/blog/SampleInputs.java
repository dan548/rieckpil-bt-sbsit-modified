package de.rieckpil.blog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class SampleInputs {

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dateField;

  private Double doubleField;
  private Integer numberField;
  private String textField;
  private String colorField;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private Date dateTimeField;
}
