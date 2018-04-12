FROM airhacks/glassfish
COPY ./target/kwetter2.war ${DEPLOYMENT_DIR}
