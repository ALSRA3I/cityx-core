FROM ubuntu:latest
LABEL authors="alabr"

ENTRYPOINT ["top", "-b"]