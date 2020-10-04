package anis.com.brewery.model.events;

import anis.com.brewery.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -2469058609501373078L;

    private BeerDto beerDto;
}
