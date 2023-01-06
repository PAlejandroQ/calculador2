#!/bin/bash
set -x

NODE_IP=$(kubectl get nodes -o jsonpath='{ $.items[0].status.addresses[?(@.type=="InternalIP")].address }')
NODE_PORT=$(kubectl get svc calculador-service -o=jsonpath='{.spec.ports[0].nodePort}')
./gradlew acceptanceTest -D calculator.url=http://${NODE_IP}:${NODE_PORT}
