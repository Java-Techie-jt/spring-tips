package com.javatechie.service;

import com.javatechie.repository.AccountRepository;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class AccountServiceProxy extends AccountService{

    private TransactionInterceptor transactionInterceptor;

    private AccountService targetClass;

    public AccountServiceProxy(AccountRepository accountRepository,
                               TransactionInterceptor transactionInterceptor,
                               AccountService targetClass) {
        super(accountRepository);
        this.transactionInterceptor = transactionInterceptor;
        this.targetClass=targetClass;
    }

    @Override
    public String transfer(Long fromId, Long toId, double amount) {
//        //get transaction()
////        return super.transfer(fromId, toId, amount);
////        //commit the tx
////        //rollback tx

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
                    return targetClass.transfer(fromId, toId, amount); // delegate actual logic
                }

                @Override
                public Object getThis() {
                    return targetClass;
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
