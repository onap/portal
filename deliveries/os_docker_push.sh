#!/bin/bash
# Builds and pushes SNAPSHOT versions of Portal images

# be verbose
set -x

# Establish environment variables
source $(dirname $0)/.env
if [ $1 ]; then
  source $(dirname $0)/$1
fi

if [ -z "$PORTAL_VERSION" ]; then
    echo "PORTAL_VERSION not set"
    exit 1
fi

TIMESTAMP=$(date +%C%y%m%dT%H%M%S)
# commenting the following two configurations as lf team preferred the uncommented format below
#export VERSION="${PORTAL_VERSION}-${TIMESTAMP}"
#export LATEST="latest"

export VERSION="${PORTAL_VERSION}-STAGING-${TIMESTAMP}"
export LATEST="${PORTAL_VERSION}-STAGING-latest"

./os_docker_base.sh $1
