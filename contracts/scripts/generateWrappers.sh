#!/usr/bin/env bash

set -e
set -o pipefail

targetFileIn=(
"src/main/resources/eip20/solidity/ERC20"
"src/main/resources/eip165/solidity/ERC165"
"src/main/resources/eip721/solidity/ERC721"
"src/main/resources/eip721/solidity/ERC721Enumerable"
"src/main/resources/eip721/solidity/ERC721Metadata")

targetPackageOut=(
"org.aimanj.contracts.eip20.generated"
"org.aimanj.contracts.eip165.generated"
"org.aimanj.contracts.eip721.generated"
"org.aimanj.contracts.eip721.generated"
"org.aimanj.contracts.eip721.generated")

mkdir -p ../build/

for i in ${!targetFileIn[@]}; do
    solcFileIn=${targetFileIn[$i]}
    packageOut=${targetPackageOut[$i]}
    dirName=$(dirname $solcFileIn)
    fileName=$(basename $solcFileIn)

    cd ../$dirName
    contractsRootDir="../../../../../"

    echo "Compiling Solidity file ${fileName}.sol:"
    solc --abi --optimize --overwrite ${fileName}.sol -o ${contractsRootDir}/build/
    echo "Complete"

    echo "Generating aiManj bindings"
    aiManj solidity generate \
        -a ${contractsRootDir}build/${fileName}.abi \
        -p ${packageOut} \
        -o ${contractsRootDir}/src/main/java/ > /dev/null
    echo "Complete"

    cd -
done
