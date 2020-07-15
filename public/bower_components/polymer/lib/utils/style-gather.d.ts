// tslint:disable:variable-name Describing an API that's defined elsewhere.

import {DomModule} from '../elements/dom-module.js';

import {resolveCss} from './resolve-url.js';

export {stylesFromModules};


/**
 * Returns a list of <style> elements in a space-separated list of `dom-module`s.
 *
 * @returns Array of contained <style> elements
 */
declare function stylesFromModules(moduleIds: string): HTMLStyleElement[];

export {stylesFromModule};


/**
 * Returns a list of <style> elements in a given `dom-module`.
 * Styles in a `dom-module` can come either from `<style>`s within the
 * first `<template>`, or else from one or more
 * `<link rel="import" type="css">` links outside the template.
 *
 * @returns Array of contained styles.
 */
declare function stylesFromModule(moduleId: string): HTMLStyleElement[];

export {stylesFromTemplate};


/**
 * Returns the `<style>` elements within a given template.
 *
 * @returns Array of styles
 */
declare function stylesFromTemplate(template: HTMLTemplateElement, baseURI?: string): HTMLStyleElement[];

export {stylesFromModuleImports};


/**
 * Returns a list of <style> elements  from stylesheets loaded via `<link rel="import" type="css">` links within the specified `dom-module`.
 *
 * @returns Array of contained styles.
 */
declare function stylesFromModuleImports(moduleId: string): HTMLStyleElement[];

export {cssFromModules};


/**
 * Returns CSS text of styles in a space-separated list of `dom-module`s.
 * Note: This method is deprecated, use `stylesFromModules` instead.
 *
 * @returns Concatenated CSS content from specified `dom-module`s
 */
declare function cssFromModules(moduleIds: string): string;

export {cssFromModule};


/**
 * Returns CSS text of styles in a given `dom-module`.  CSS in a `dom-module`
 * can come either from `<style>`s within the first `<template>`, or else
 * from one or more `<link rel="import" type="css">` links outside the
 * template.
 *
 * Any `<styles>` processed are removed from their original location.
 * Note: This method is deprecated, use `styleFromModule` instead.
 *
 * @returns Concatenated CSS content from specified `dom-module`
 */
declare function cssFromModule(moduleId: string): string;

export {cssFromTemplate};


/**
 * Returns CSS text of `<styles>` within a given template.
 *
 * Any `<styles>` processed are removed from their original location.
 * Note: This method is deprecated, use `styleFromTemplate` instead.
 *
 * @returns Concatenated CSS content from specified template
 */
declare function cssFromTemplate(template: HTMLTemplateElement, baseURI: string): string;

export {cssFromModuleImports};


/**
 * Returns CSS text from stylesheets loaded via `<link rel="import" type="css">`
 * links within the specified `dom-module`.
 *
 * Note: This method is deprecated, use `stylesFromModuleImports` instead.
 *
 * @returns Concatenated CSS content from links in specified `dom-module`
 */
declare function cssFromModuleImports(moduleId: string): string;
