# riemann-burst

Tools to support Riemann testing by bursting events.

## Usage

Start a local Riemann, for example using [riemann-playground](https://github.com/Intelliplan/riemann-playground). Load riemann-burst/core.clj and it will try to connect with a Riemann on 127.0.0.1:5555 (default Riemann TCP port) and send any burst as stated in that file. Reload the file many times and the bursts will be run in parallel.

## License

Copyright © 2014 Intelliplan

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
