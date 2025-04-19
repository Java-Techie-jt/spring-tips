package com.javatechie.config;

import com.javatechie.repository.AccountRepository;
import com.javatechie.service.AccountService;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class AccountService_ProxyNew extends AccountService {

    private final TransactionInterceptor transactionInterceptor;
    private final AccountService target;

    public AccountService_ProxyNew(AccountRepository accountRepository,AccountService target, TransactionInterceptor transactionInterceptor) {
        super(accountRepository);
        this.target = target;
        this.transactionInterceptor = transactionInterceptor;
    }

    @Override
    public String transfer(Long fromId, Long toId, double amount) {
        try {
            // Construct MethodInvocation manually (pseudocode)
            Method method = AccountService.class.getMethod("transfer", Long.class, Long.class, double.class);

            MethodInvocation invocation = new MethodInvocation() {
                @Override
                public Method getMethod() {
                    return method;
                }

                @Override
                public Object[] getArguments() {
                    return new Object[]{fromId, toId, amount};
                }

                @Override
                public Object proceed() throws Throwable {
                    return target.transfer(fromId, toId, amount); // delegate actual logic
                }

                @Override
                public Object getThis() {
                    return target;
                }

                @Override
                public AccessibleObject getStaticPart() {
                    return method;
                }


            };

            // This is where Spring applies @Transactional and AOP logic:
            transactionInterceptor.invoke(invocation);

        } catch (Throwable ex) {
            throw new RuntimeException("Transaction failed", ex);
        }
        return "";
    }
}
