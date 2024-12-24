package com.jaejoo.fitdobatch.lambda;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.jaejoo.fitdobatch.FitdoBatchApplication;

public class BatchLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler=new SpringBootProxyHandlerBuilder<AwsProxyRequest>()
                    .defaultProxy()
                    .asyncInit()
                    .springBootApplication(FitdoBatchApplication.class)
                    .buildAndInitialize();
        } catch (ContainerInitializationException e) {
            throw new RuntimeException("Fail to start Spring Boot Application", e);
        }
    }


    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest input, Context context) {
        return handler.proxy(input, context);
    }
}
