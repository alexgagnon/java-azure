#!/bin/bash

set -eou pipefail

# install the CDTS templates into local maven
cd gocwebtemplate-sample-spring
mvn package
cd ../

# install certs
mkcert -pkcs12 localhost
cp localhost.p12 ./api/src/main/resources/
cp localhost.p12 ./site/src/main/resources/
rm localhost.p12