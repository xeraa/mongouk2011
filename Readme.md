# MongoUK2011 Morphia Presentation

[![Build Status](https://travis-ci.org/xeraa/mongouk2011.png?branch=master)](https://travis-ci.org/xeraa/mongouk2011)

This is a simple test project I used to show some Morphia features at MongoUK2011: [http://www.10gen.com/conferences/mongouk2011#agenda](http://www.10gen.com/conferences/mongouk2011#agenda)

## Entities
The basic structure of the Entities looks like this: ![Code diagram](https://github.com/xeraa/mongouk2011/raw/master/diagram.png)


## Presentation
The presentation is also available at: [https://prezi.com/ykqpljl6-wem/mongouk2011/](https://prezi.com/ykqpljl6-wem/mongouk2011/)


## Requirements
*   JDK7+
*   Maven2+ (tested with Maven2 and Maven3)
*   MongoDB 2.0+

An IDE like Eclipse or IntelliJ is highly recommended but not required.


## Getting started
Inside the base folder (where the pom.xml file is located) run the following commands to get started - this assumes that you have MongoDB running on the default port:

    mvn install
    mvn test


## License
    Copyright (c) 2011 - 2013, Philipp Krenn
    All rights reserved.
   
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:
        * Redistributions of source code must retain the above copyright
          notice, this list of conditions and the following disclaimer.
        * Redistributions in binary form must reproduce the above copyright
          notice, this list of conditions and the following disclaimer in the
          documentation and/or other materials provided with the distribution.
        * Neither the name of the authors nor the names of its contributors
          may be used to endorse or promote products derived from this
          software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS" AND ANY
    EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
    WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
    DISCLAIMED. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY DIRECT,
    INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
    (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
    ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
