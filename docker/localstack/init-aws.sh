#!/bin/bash
echo "Initializing SQS Queues..."

awslocal sqs create-queue --queue-name reservation-queue

REDRIVE_POLICY='"{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:reservation-queue-dlq\",\"maxReceiveCount\":\"3\"}"'


awslocal sqs create-queue \
  --queue-name reservation-queue \
  --attributes "{\"RedrivePolicy\": $(echo $REDRIVE_POLICY)}"


# awslocal sqs set-queue-attributes \
#   --queue-url http://localhost:4566/000000000000/reservation-queue \
#   --attributes "{\"RedrivePolicy\": $(echo $REDRIVE_POLICY)}"

echo "SQS Queues initialized successfully!"