#!/bin/bash

set -e

host="$1"
shift
cmd="$@"

until nc -z $host; do
  >&2 echo "Target Server is unavailable - sleeping"
  sleep 1
done

>&2 echo "Target Server is up - executing command"
exec $cmd