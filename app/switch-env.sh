function switch_dev() {
    localIp=$(ifconfig en0 | grep inet | grep -v inet6 | awk '{print($2)}')
    echo $localIp
    switch_ip $localIp
}

function switch_prod() {
    #prodIp=$MyPocketProdIp
    prodIp=$1
    echo ${prodIp}
    switch_ip prodIp
}

function switch() {
    env=$1
    echo environment is $env

    if [ $env == "dev" ]; then
        switch_dev
    elif [ $env == "prod" ]; then
        ip=$2
        echo ip is $ip
        switch_prod $ip
    else
        usage
    fi
}

function switch_ip() {
    newIp=$1
    configFile="src/main/assets/configure"
    sed -i "_bak" "s/^host = http:\/\/.*:8001$/host = http:\/\/${newIp}:8001/g" ${configFile}
    cat ${configFile}
}

function usage() {
    echo "./switch-dev env[dev|prod] [ip]"
}

switch $1 $2