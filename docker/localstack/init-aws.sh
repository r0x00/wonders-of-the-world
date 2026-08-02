#!/bin/bash
echo "Initializing SQS Queues..."

QUEUE_NAME=$1


function createQueue {
  queueName=$1

  awslocal sqs create-queue --queue-name "${queueName}"

  REDRIVE_POLICY='"{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:'$queueName'-dlq\",\"maxReceiveCount\":\"3\"}"'


  awslocal sqs create-queue \
    --queue-name "${queueName}" \
    --attributes "{\"RedrivePolicy\": $(echo $REDRIVE_POLICY)}"


  # awslocal sqs set-queue-attributes \
  #   --queue-url "http://localhost:4566/000000000000/${queueName}" \
  #   --attributes "{\"RedrivePolicy\": $(echo $REDRIVE_POLICY)}"

  echo "SQS Queues initialized successfully!"

}

createQueue "check-payment-queue"
createQueue "check-stock-queue"