#!/bin/bash

LIST_URL=https://publicsuffix.org/list/public_suffix_list.dat
LIST_NAME=public_suffix_list.dat
LIST_PATH=./urlparser/src/jvmMain/resources/$LIST_NAME
BUILD_DIR=.build
NEW_LIST_PATH=$BUILD_DIR/$LIST_NAME

set -e

mkdir -p $BUILD_DIR

curl -f -o $NEW_LIST_PATH $LIST_URL

ORIG_HASH=`md5sum $LIST_PATH | awk '{print $1}'`
NEW_HASH=`md5sum $NEW_LIST_PATH | awk '{print $1}'`

if [ "$ORIG_HASH" != "$NEW_HASH" ]; then
    echo "Updating suffix list"
    cp $NEW_LIST_PATH $LIST_PATH
    ./gradlew bumpBuildVersion
else
    echo "Suffix lists identical. No update needed"
fi

rm -rf $BUILD_DIR