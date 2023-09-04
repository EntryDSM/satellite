FROM eclipse-temurin:17-jre-focal

EXPOSE 8080
ENV TZ=Asia/Seoul

ENV AWS_S3_BUCKET=dsm-repo \
    AWS_S3_URL=https://s3.ap-northeast-2.amazonaws.com/dsm-repo/ \
    DOC_FOLDER=images/document \
    PROFILE_FOLDER=images/user-profile \
    PDF_FOLDER=files \ 
    DB_POOL_SIZE='10' \
    DDL=update \
    JWT_ACCESS_EXP='10000000000' \
    JWT_REFRESH_EXP='10000000000' \
    GOOGLE_BASE_URL=https://accounts.google.com/o/oauth2/v2/auth \
    REDIRECT_URL=https://user.dsm-repo.com/sign-in \
    PDF_FOLDER=files

ARG AWS_ACCESS_KEY_ID
ENV AWS_ACCESS_KEY_ID ${AWS_ACCESS_KEY_ID}
ARG AWS_SECRET_ACCESS_KEY
ENV AWS_SECRET_ACCESS_KEY ${AWS_SECRET_ACCESS_KEY}

ARG GOOGLE_CLIENT_ID
ENV GOOGLE_CLIENT_ID ${GOOGLE_CLIENT_ID}
ARG GOOGLE_SECRET_KEY
ENV GOOGLE_SECRET_KEY ${GOOGLE_SECRET_KEY}
ARG JWT_SECRET_KEY
ENV JWT_SECRET_KEY ${JWT_SECRET_KEY}

ARG DB_HOST
ENV MARIA_DB_HOST ${DB_HOST}
ENV MONGO_DB_HOST ${DB_HOST}

ENV MARIA_DB_DATABASE repo
ARG MARIA_DB_PASSWORD
ENV MARIA_DB_PASSWORD ${MARIA_DB_PASSWORD}
ARG MARIA_DB_PORT
ENV MARIA_DB_PORT ${MARIA_DB_PORT}
ARG MARIA_DB_USERNAME
ENV MARIA_DB_USERNAME ${MARIA_DB_USERNAME}

ENV MONGO_DB_NAME repo
ARG MONGO_DB_PASSWORD
ENV MONGO_DB_PASSWORD ${MONGO_DB_PASSWORD}
ARG MONGO_DB_USERNAME
ENV MONGO_DB_USERNAME ${MONGO_DB_USERNAME}

ARG REDIS_HOST
ENV REDIS_HOST ${REDIS_HOST}
ARG REDIS_PASS
ENV REDIS_PASS ${REDIS_PASS}
ENV REDIS_PORT=6379

ARG SCHOOL_YEAR_ADMIN_KEY
ENV SCHOOL_YEAR_ADMIN_KEY ${SCHOOL_YEAR_ADMIN_KEY}
ARG SCHOOL_YEAR_ID
ENV SCHOOL_YEAR_ID ${SCHOOL_YEAR_ID}

ARG SENTRY_DSN
ENV SENTRY_DSN ${SENTRY_DSN}

COPY ./satellite-infrastructure/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
