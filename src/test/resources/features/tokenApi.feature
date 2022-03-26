@run
Feature: Toekn Api

//@tokenApi //@regression //@smoke
 
Scenario: Get token

Given User creates request data with "beydjavid1@gmail.com" and "@@Javid21"
And User submits request to token API
And User validates if status code is 200
Then User retrieves access token from response

