#!/bin/bash

set -eou pipefail

. env.properties

# install cert
mkcert -p12-file ./src/main/resources/local.p12 -pkcs12 localhost
