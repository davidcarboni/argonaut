[![Build Status](https://travis-ci.org/davidcarboni/jjtemplate.svg?branch=master)](https://travis-ci.org/davidcarboni/jjtemplate)

# JJtemplate

## What is it

Json Java templating is a basic templating engine thet takes Json and a text template as input.

## How does it work

JJtemplate borrows a whiff of Mustache, but pares it back to almost nothing. This is very, very logicless. 

Given the following Json:

    {
      "person": {
        "name": "David Carboni",
        "address": [
          "111 Hobbiton Rd",
          "Middle earth",
          "ME1 2JJ"
        ],
        "project": {
          "name": "jjtemplate",
          "description": "Simply getting Json data into templates in Java."
        }
      }
    }

The following are valid instructions:

 * `{{person.name}}` produces "David Carboni".
 * `{{person.project as project}}{{project.name}}` produces "jjtemplate".
 * `{{line in person.address}}{{line}} {{end}}` produces "111 Hobbiton Rd Middle earth ME1 2JJ ".

NB the aim of this project is "to enable json to be inserted into templates". That means you don't get to manipulate values or execute code in the template. It's all about selecting and printing values from a Json document. For example, if you need to comma-separate the address above, you should do that in your code and add the result to the json document.

And that's it.

David

