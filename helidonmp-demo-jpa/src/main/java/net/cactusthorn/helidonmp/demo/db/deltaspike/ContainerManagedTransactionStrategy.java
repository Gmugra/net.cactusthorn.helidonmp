package net.cactusthorn.helidonmp.demo.db.deltaspike;

import org.apache.deltaspike.jpa.spi.transaction.TransactionStrategy;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Alternative;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

/*
 * This class factually copied from deltaspike-data sources.
 * we need it to force(using @Alternative) deltaspike-data to use ContainerManagedTransactionStrategy
 */

@Dependent
@Alternative
@Priority(Interceptor.Priority.APPLICATION)
public class ContainerManagedTransactionStrategy implements TransactionStrategy {

  private static final long serialVersionUID = 0L;

  @Override
  public Object execute(InvocationContext invocationContext) throws Exception {
    return invocationContext.proceed();
  }
}
