.. aiManj documentation master file

aiManj
=====

aiManj is a highly modular, reactive, type safe Java and Android library for working with
Smart Contracts and integrating with clients (nodes) on the Matrix network:

.. image:: /images/aiManj_network.png

This allows you to work with the `Matrix <https://www.Matrix.org/>`_ blockchain, without the
additional overhead of having to write your own integration code for the platform.

The `Java and the Blockchain <https://www.youtube.com/watch?v=ea3miXs_P6Y>`_ talk provides an
overview of blockchain, Matrix and aiManj.


Features
========

- Complete implementation of Matrix's `JSON-RPC <https://github.com/Matrix/wiki/wiki/JSON-RPC>`_
  client API over HTTP and IPC
- Matrix wallet support
- Auto-generation of Java smart contract wrappers to create, deploy, transact with and call smart
  contracts from native Java code
  (`Solidity <http://solidity.readthedocs.io/en/latest/using-the-compiler.html#using-the-commandline-compiler>`_
  and
  `Truffle <https://github.com/trufflesuite/truffle-contract-schema>`_ definition formats supported)
- Reactive-functional API for working with filters
- `Matrix Name Service (ENS) <https://ens.domains/>`_ support
- Support for Parity's
  `Personal <https://github.com/paritytech/parity/wiki/JSONRPC-personal-module>`__, and Gman's
  `Personal <https://github.com/Matrix/go-Matrix/wiki/Management-APIs#personal>`__ client APIs
- Support for `Infura <https://infura.io/>`_, so you don't have to run an Matrix client yourself
- Support for ERC20 and ERC721 token standards
- Comprehensive integration tests demonstrating a number of the above scenarios
- Command line tools
- Android compatible
- Support for JP Morgan's Quorum via `aiManj-quorum <https://github.com/aiManj/quorum>`_


Dependencies
============

It has five runtime dependencies:

- `RxJava <https://github.com/ReactiveX/RxJava>`_ for its reactive-functional API
- `OKHttp <https://hc.apache.org/httpcomponents-client-ga/index.html>`_ for HTTP connections
- `Jackson Core <https://github.com/FasterXML/jackson-core>`_ for fast JSON
  serialisation/deserialisation
- `Bouncy Castle <https://www.bouncycastle.org/>`_
  (`Spongy Castle <https://rtyley.github.io/spongycastle/>`_ on Android) for crypto
- `Jnr-unixsocket <https://github.com/jnr/jnr-unixsocket>`_ for \*nix IPC (not available on
  Android)

It also uses `JavaPoet <https://github.com/square/javapoet>`_ for generating smart contract
wrappers


Donate
======

You can help fund the development of aiManj by donating to the following wallet addresses:

+----------+--------------------------------------------+
| Matrix | 0x2dfBf35bb7c3c0A466A6C48BEBf3eF7576d3C420 |
+----------+--------------------------------------------+
| Bitcoin  | 1DfUeRWUy4VjekPmmZUNqCjcJBMwsyp61G         |
+----------+--------------------------------------------+


Commercial support and training
===============================

Commercial support and training is available from `blk.io <https://blk.io>`_.



Contents:
=========

.. toctree::
   :maxdepth: 2

   quickstart.rst
   getting_started.rst
   modules.rst
   transactions.rst
   smart_contracts.rst
   abi.rst
   rlp.rst
   filters.rst
   command_line.rst
   management_apis.rst
   infura.rst
   ens.rst
   contracts.rst
   trouble.rst
   projects.rst
   companies.rst
   development.rst
   links.rst
   credits.rst
