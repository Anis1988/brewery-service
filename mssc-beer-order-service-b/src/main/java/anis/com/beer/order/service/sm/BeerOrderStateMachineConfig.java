package anis.com.beer.order.service.sm;

import anis.com.beer.order.service.domain.BeerOrderEventEnum;
import anis.com.beer.order.service.domain.BeerOrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

import static anis.com.beer.order.service.domain.BeerOrderEventEnum.*;
import static anis.com.beer.order.service.domain.BeerOrderStatusEnum.*;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class BeerOrderStateMachineConfig extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum>  validateOrderAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum>  allocateOrderAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum>  validationFailureAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum>  allocationFailureAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum>  deallocateOrderAction;

    @Override
    public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states) throws Exception {
        states.withStates()
                .initial(NEW)
                .states(EnumSet.allOf(BeerOrderStatusEnum.class))
                .end(PICKED_UP)
                .end(DELIVERED)
                .end(CANCELLED)
                .end(DELIVERY_EXCEPTION)
                .end(VALIDATION_EXCEPTION)
                .end(ALLOCATION_EXCEPTION);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(NEW).target(VALIDATION_PENDING)
                .event(VALIDATE_ORDER)
                .action(validateOrderAction)
                .and().withExternal()
                .source(VALIDATION_PENDING).target(VALIDATED)
                .event(VALIDATION_PASSED)
                .and().withExternal()
                .source(VALIDATION_PENDING).target(CANCELLED)
                .event(CANCEL_ORDER)
                .and().withExternal()
                .source(VALIDATION_PENDING).target(VALIDATION_EXCEPTION)
                .event(VALIDATION_FAILED)
                .action(validationFailureAction)
                .and().withExternal()
                .source(VALIDATED).target(ALLOCATION_PENDING)
                .event(ALLOCATE_ORDER)
                .action(allocateOrderAction)
                .and().withExternal()
                .source(VALIDATED).target(CANCELLED)
                .event(CANCEL_ORDER)
                .and().withExternal()
                .source(ALLOCATION_PENDING).target(ALLOCATED)
                .event(ALLOCATION_SUCCESS)
                .and().withExternal()
                .source(ALLOCATION_PENDING).target(ALLOCATION_EXCEPTION)
                .event(ALLOCATION_FAILED)
                .action(allocationFailureAction)
                .and().withExternal()
                .source(ALLOCATION_PENDING).target(CANCELLED)
                .event(CANCEL_ORDER)
                .and().withExternal()
                .source(ALLOCATION_PENDING).target(PENDING_INVENTORY)
                .event(ALLOCATION_NO_INVENTORY)
                .and().withExternal()
                .source(ALLOCATED).target(PICKED_UP)
                .event(BEERORDER_PICKED_UP)
                .and().withExternal()
                .source(ALLOCATED).target(CANCELLED)
                .event(CANCEL_ORDER)
                .action(deallocateOrderAction);
    }
}
