package org.aimanj.codegen;

/**
 * Can be used to provide report about a code generation process.
 */
interface GenerationReporter {
    void report(String msg);
}
